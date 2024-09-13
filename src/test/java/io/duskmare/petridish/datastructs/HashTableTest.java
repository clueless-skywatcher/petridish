package io.duskmare.petridish.datastructs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HashTableTest {
    @Test
    public void testHashTableDefaultInit() {
        HashTable<String, String> ht = new HashTable<>();
        assertEquals(10, ht.getCapacity());
        assertEquals(0.70f, ht.getLoadFactor());
    }

    @Test
    public void testHashTableInitWithVals() {
        HashTable<String, String> ht = new HashTable<>(81, 0.90f);
        assertEquals(81, ht.getCapacity());
        assertEquals(0.90f, ht.getLoadFactor());
    }

    @Test
    public void testHashTableGet() {
        HashTable<String, Object> ht = new HashTable<>();
        ht.set("Name", "Hash");
        assertEquals("Hash", ht.get("Name"));
        ht.set("Name", "HashValue");
        assertEquals("HashValue", ht.get("Name"));
        assertEquals(1, ht.getElementCount());
        ht.set("Age", 27);
        assertEquals(27, ht.get("Age"));
        assertEquals(2, ht.getElementCount());
    }

    @Test
    public void testHashTableRemove() {
        HashTable<String, Object> ht = new HashTable<>();
        ht.set("Name", "Hash");
        assertEquals("Hash", ht.get("Name"));
        ht.remove("Name");
        assertNull(ht.get("Name"));
        assertEquals(0, ht.getElementCount());
    }

    @Test
    public void testHashTableRehash() {
        HashTable<String, Object> ht = new HashTable<>();
        for (int i = 0; i < 8; i++) {
            ht.set(String.format("Key%d", i), i);
        }
        assertEquals(8, ht.getElementCount());
        assertEquals(20, ht.getCapacity());

        for (int i = 0; i < 8; i++) {
            assertEquals(i, ht.get(String.format("Key%d", i)));
        }
    }

    @Test
    public void testHashTableContainsKey() {
        HashTable<String, Object> ht = new HashTable<>();
        for (int i = 0; i < 10; i++) {
            ht.set(String.format("Key%d", i), i);
        }
        for (int i = 0; i < 10; i++) {
            assertTrue(ht.containsKey(String.format("Key%d", i)));
        }
        for (int i = 10; i < 20; i++) {
            assertTrue(!ht.containsKey(String.format("Key%d", i)));
        }
    }
}
