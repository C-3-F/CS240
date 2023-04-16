package com.c3farr.familymapclient.uiModels;

import java.util.HashMap;

public class FamilyTree {
    public FamilyTreeNode rootPerson;
    public HashMap<String,String> eventColors;

    public FamilyTree(FamilyTreeNode rootPerson) {
        this.rootPerson = rootPerson;
    }
}
