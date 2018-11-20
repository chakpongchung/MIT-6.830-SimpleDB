package simpledb;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import simpledb.TestUtil.SkeletonFile;
import simpledb.systemtest.SimpleDbTestBase;
import simpledb.systemtest.SystemTestUtil;

import java.util.NoSuchElementException;

import static junit.framework.Assert.assertEquals;

public class CatalogTest extends SimpleDbTestBase {
    private static String name = "test";
	private String nameThisTestRun;
    
    @Before public void addTables() {
        Database.getCatalog().clear();
		nameThisTestRun = SystemTestUtil.getUUID();
        Database.getCatalog().addTable(new SkeletonFile(-1, Utility.getTupleDesc(2)), nameThisTestRun);
        Database.getCatalog().addTable(new SkeletonFile(-2, Utility.getTupleDesc(2)), name);
    }

    /**
     * Unit test for Catalog.getTupleDesc()
     */
    @Test public void getTupleDesc() {
        TupleDesc expected = Utility.getTupleDesc(2);
        TupleDesc actual = Database.getCatalog().getTupleDesc(-1);

        assertEquals(expected, actual);
    }

    /**
     * Unit test for Catalog.getTableId()
     */
    @Test public void getTableId() {
        assertEquals(-2, Database.getCatalog().getTableId(name));
        assertEquals(-1, Database.getCatalog().getTableId(nameThisTestRun));
        
        try {
            Database.getCatalog().getTableId(null);
            Assert.fail("Should not find table with null name");
        } catch (NoSuchElementException e) {
            // Expected to get here
        }
        
        try {
            Database.getCatalog().getTableId("foo");
            Assert.fail("Should not find table with name foo");
        } catch (NoSuchElementException e) {
            // Expected to get here
        }
    }

    /**
     * Unit test for Catalog.getDatabaseFile()
     */
    @Test public void getDatabaseFile() {
        DbFile f = Database.getCatalog().getDatabaseFile(-1);

        // NOTE(ghuo): we try not to dig too deeply into the DbFile API here; we
        // rely on HeapFileTest for that. perform some basic checks.
        assertEquals(-1, f.getId());
    }

    /**
     * JUnit suite target
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CatalogTest.class);
    }
}

