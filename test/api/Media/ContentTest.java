package api.Media;

import org.junit.jupiter.api.Assertions;

class ContentTest {

    @org.junit.jupiter.api.Test
    void contentTest() {
        Content media = new Content("A","aa","Yes","abc",Category.ACTION);
        Assertions.assertEquals(media.getTitle(),"A");
        Assertions.assertEquals(media.getDescription(),"aa");
        Assertions.assertEquals(media.getAgeRestriction(),"Yes");
        Assertions.assertEquals(media.getCategory(),Category.ACTION);
        Assertions.assertEquals(media.getStars(),"abc");
    }


}