/*
 * Copyright (c) 2016. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.json;

import com.lscsoft.jfa.commons.json.example.Person;
import junit.framework.TestCase;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;


public class JsonUtilsTest extends TestCase {

    private JSONObject jsonObject;

    @Before
    public void setUp() throws Exception {

        super.setUp();

        String json = "{\"Id\":1,\"Name\":\"bond\",\"Contact\":{\"MobilePhone\":\"13316809070\",\"Email\":\"zzdjavajob@163.com\"}}";

        jsonObject = JSONObject.fromObject(json);
    }

    @Test
    public void testToBean() throws Exception {

        Person person = JsonUtils.toBean(jsonObject, Person.class);

        assertNotNull(person);

        assertEquals(person.getId(), 1);

        assertEquals(person.getMobilePhone(), "13316809070");
    }
}