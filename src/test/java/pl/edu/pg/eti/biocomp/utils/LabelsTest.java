package pl.edu.pg.eti.biocomp.utils;

import junit.framework.Assert;
import junit.framework.TestCase;

public class LabelsTest extends TestCase {
    public void testNext() throws Exception {
        Assert.assertEquals("abd", Labels.next("abc"));
        Assert.assertEquals("aa", Labels.next("z"));
        Assert.assertEquals("zb", Labels.next("za"));
        Assert.assertEquals("aaaa", Labels.next("zzz"));
        Assert.assertEquals("zzzb", Labels.next("zzza"));
    }

}