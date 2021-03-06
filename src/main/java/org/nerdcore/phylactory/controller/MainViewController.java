package org.nerdcore.phylactory.controller;

import org.nerdcore.phylactory.localview.*;
import org.nerdcore.phylactory.model.*;
import org.nerdcore.phylactory.repository.TestRepository;
import org.nerdcore.phylactory.services.TestServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainViewController {

    @Autowired
    static TestRepository testRepository;

    @Autowired
    static TestServices testServices;

    public static MainView mainView;

    public static Party currentParty;

    public static Map<Integer, Party> allParties = new HashMap<>();

    public static void initView(){
        System.out.println(testRepository);
        System.out.println(testServices);
        for(Test t: testRepository.findAll()){
            System.out.println(t);
        }

        allParties = DataController.getAllPartiesMap();

        currentParty = allParties.get(0);
        currentParty.rollForInitiative();
        mainView = new MainView(currentParty);
    }

    //TODO: static method call to roll party and update party display panel

    public static void totalPartyReroll(){
        currentParty.rollForInitiative();
        mainView.rebuild(currentParty);
    }

    public static void totalPartyReroll(Party party){
        mainView.rebuild(party.rollForInitiative());
    }

    public static void sortParty(Party party, JTable dataTable){
        for(int i = 0; i < party.getAllCombatants().size(); i++){
            Combatant c = party.getAllCombatants().get(i);
            Object tableValue = dataTable.getModel().getValueAt(i, 1);
            if(tableValue instanceof String){
                c.setInitiative(Integer.parseInt((String)tableValue));
            } else if (tableValue instanceof Integer){
                c.setInitiative((Integer)tableValue);
            }
        }
        party.sort();
        mainView.rebuild(party);
    }


    public static void buildAddCombatantView(){
        JFrame addFrame = new AddCombatantView();
    }

    public static void destroyFrame(JFrame jFrame){
        jFrame.dispose();
    }

    public static Combatant extractCombatantFromNewSingleCombatantPanel(NewSingleCombatantPanel panel){
        //NewSingleCombatantPanel stores the data-storing components in a Map
        //we access this map to access the data stored therein
        //Mostly JTextFields, COMBATANT_TYPE is stored in a series of radio buttons
        Map<String, Component> componentMap = panel.getPanelComponents();

        Combatant c = new Combatant(((JTextField)componentMap.get("nameField")).getText(),
                Integer.parseInt(((JTextField)componentMap.get("dexField")).getText()));

        Component[] combatantTypeRadioButtons = ((JPanel)componentMap.get("combatantTypeRadioPanel")).getComponents();
        for(Component comp : combatantTypeRadioButtons){
            JRadioButton b = (JRadioButton)comp;
            if(b.isSelected()) c.setCombatantType(COMBATANT_TYPE.valueOf(b.getActionCommand()));
        }
        if( ((JTextField)componentMap.get("extraBonusField")).getText() != null && Integer.parseInt(((JTextField)componentMap.get("extraBonusField")).getText()) != 0){
            c.addInitiativeBonus("genericBonus", Integer.parseInt(((JTextField)componentMap.get("extraBonusField")).getText()));
        }
        c.setMaxHP(Integer.parseInt(((JTextField)componentMap.get("maxHPField")).getText()));
        c.setArmorClass(Integer.parseInt(((JTextField)componentMap.get("acField")).getText()));

        return c;
    }

    public static void addCombatant(NewSingleCombatantPanel panel){
        Combatant c = extractCombatantFromNewSingleCombatantPanel(panel);
        c.rollForInitiative();
        currentParty.addCombatant(c);
        mainView.rebuild(currentParty);
    }

    public static void addCombatant(String name, COMBATANT_TYPE combatantType, int dexterity, int bonus, int maxHP, int armorClass){
        Combatant c = new Combatant(name, dexterity);

        if(bonus != 0){
            c.addInitiativeBonus("SingleNewCombatantBonus", bonus);
        }
        c.setCombatantType(combatantType);
        c.setMaxHP(maxHP);
        c.setArmorClass(armorClass);
        c.rollForInitiative();
        currentParty.addCombatant(c);
        mainView.rebuild(currentParty);
    }

    public static void clearParty(){
        currentParty.clearParty();
        mainView.rebuild(currentParty);
    }

    public static void buildChoosePartyView(){
        JFrame selectPartyFrame = new ChoosePartyView(new ArrayList<>(allParties.values()));
    }

    public static void newPartyView(Party party, JFrame frameToDestroy){
        destroyFrame(frameToDestroy);
        currentParty = party;
        mainView.rebuild(currentParty);

    }

    public static void rollMonstersInParty(){
        for(Combatant c : currentParty.getAllCombatants()){
            if(c.getCombatantType() != COMBATANT_TYPE.PLAYER){
                c.rollForInitiative();
            }
        }
        currentParty.sort();
        mainView.rebuild(currentParty);
    }

    public static void buildCreateNewPartyView(){
        Party p = new Party();
        p.addCombatant(new Combatant());
        JFrame frame = new CreateNewPartyView(p);
    }

    public static void saveNewParty(ArrayList<NewSingleCombatantPanel> panels, String partyName, String partyType){
        System.out.println("Writing new party to local storage");
        Party saveParty = new Party();
        for(NewSingleCombatantPanel panel : panels){
            saveParty.addCombatant(extractCombatantFromNewSingleCombatantPanel(panel));
        }
        saveParty.setPartyType(PARTY_TYPE.valueOf(partyType));
        if(partyName.length() > 0){
            saveParty.setPartyName(partyName);
        }
        allParties.put(allParties.keySet().size(),saveParty);
        for(Integer key : allParties.keySet()){
            System.out.println(key);
            System.out.println(allParties.get(key));
        }
    }

    public static void saveAllData(){
        System.out.println("saving data");
        DataController.writeAllPartiesToFile(allParties);
        System.out.println("save complete");
    }

    public static void editParty(Party p){
        System.out.println("Editing party");
    }

    public static void addMonstersToParty(Party p){
        for(Combatant c : p.getAllCombatants()){
            currentParty.addCombatant(c);
        }
        mainView.rebuild(currentParty);
    }

    public static void buildAddMonsterView(){
        JFrame addMonsterFrame = new ChoosePartyView(allParties, LIST_CLASS_OPTION.ADD_MONSTERS);
    }
}
