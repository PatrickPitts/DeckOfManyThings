package org.nerdcore.phylactory.services;

import org.nerdcore.phylactory.model.Test;
import org.nerdcore.phylactory.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServices {

    @Autowired
    private TestRepository testRepository;

    public List<Test> findAll(){
        System.out.println(testRepository);
        return testRepository.findAll();
    }
}
