package com.worldbestsoft.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.Constants;
import com.worldbestsoft.dao.LookupDao;
import com.worldbestsoft.model.LabelValue;
import com.worldbestsoft.model.Role;
import com.worldbestsoft.service.LookupManager;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("lookupManager")
public class LookupManagerImpl implements LookupManager {
    @Autowired
    LookupDao dao;
    
    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllRoles() {
        List<Role> roles = dao.getRoles();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (Role role1 : roles) {
            list.add(new LabelValue(role1.getName(), role1.getName()));
        }

        return list;
    }
    
    @Override
    public List<LabelValue> getAllCustomerType(Locale locale) {
    		ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale);
    		List<LabelValue> customerTypeList = new ArrayList<LabelValue>();
    		//personal
    		customerTypeList.add(new LabelValue(resourceBundle.getString("customerType.100"), "100"));
    		//company
    		customerTypeList.add(new LabelValue(resourceBundle.getString("customerType.200"), "200"));
    		return customerTypeList;
    } 
    
}
