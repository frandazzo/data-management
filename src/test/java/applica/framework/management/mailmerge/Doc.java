package applica.framework.management.mailmerge;

/**
 * Applica
 * User: Alberto Montemurro
 * Date: 10/15/14
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Doc {

    private String title;
    private String section1;

    public String getSection2() {
        return section2;
    }

    public void setSection2(String section2) {
        this.section2 = section2;
    }

    public String getSection1() {
        return section1;
    }

    public void setSection1(String section1) {
        this.section1 = section1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String section2;
}
