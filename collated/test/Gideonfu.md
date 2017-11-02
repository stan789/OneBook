# Gideonfu
###### \java\seedu\address\model\person\RemarkTest.java
``` java
    @Test
    public void isValidRemark() {
        // invalid addresses
        assertFalse(Remark.isValidRemark("")); // empty string
        assertFalse(Remark.isValidRemark(" ")); // spaces only

        // valid addresses
        assertTrue(Remark.isValidRemark("owe $10"));
        assertTrue(Remark.isValidRemark("~")); // one character
        assertTrue(Remark.isValidRemark("Meet for lunch 21/10 6pm Nex")); // long remark
    }
}
```
