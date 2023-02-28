package DemoqaPackage;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.event.KeyEvent;

public class DemoqaTCs extends DriverClass {
    /**
     * 1. Go to the website demoqa.com(url: https://demoqa.com/ );
     * 2. Get the title of the website;
     */
    @Test(priority = 1)
    void validateTheTitle() {
        driver.get("https://demoqa.com/");
        String actualResult = driver.getTitle();
        String expectedResult = "DEMOQA";
//        Expected result: After opening demoqa.com you get the title ToolsQA – “DEMOQA”
        Assert.assertEquals(actualResult, expectedResult);
    }

    /**
     * url: https://demoqa.com/
     * 1. Go to the website demoqa.com
     * 2. Click the “interaction” button on top menu list;
     * 3.get URL
     */
    @Test(priority = 2)
    void interactionButton() {
        driver.get("https://demoqa.com/");
        WebElement interaction = driver.findElement(By.xpath("//h5[text()='Interactions']/../.."));
        interaction.click();
        String actualResult = driver.getCurrentUrl();
        String expectedResult = "https://demoqa.com/interaction";
//        Expected result: You should be able to get current url “ https://demoqa.com/interaction/ “
        Assert.assertEquals(actualResult, expectedResult);
    }

    /**
     * 1. Go to the website demoqa.com;
     * 2. Click on the “interaction” button on top menu list;
     * 3. Click on the “Resizable“ link on left sidebar;
     */
    @Test(priority = 3)
    void resizeInInteraction() {
        driver.get("https://demoqa.com/");
        WebElement interaction = driver.findElement(By.xpath("//h5[text()='Interactions']/../.."));
        interaction.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 300)");
        WebElement resizable = driver.findElement(By.xpath("//span[text()='Resizable']"));
        resizable.click();
        String expectedResult = "https://demoqa.com/resizable";
        String actualResult = driver.getCurrentUrl();
//        Expected result: You should be able to get current url  “ https://demoqa.com/resizable/ ”
        Assert.assertEquals(actualResult, expectedResult);
    }

    /**
     * 1. Go to the website demoqa.com;
     * 2. Click on the “Widgets” button on top menu list;
     * 3. Click on the “Tool Tips“ link on left sidebar;
     */
    @Test(priority = 4)
    void widgetValidation() {
        driver.get("https://demoqa.com/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement widgets = driver.findElement(By.xpath("//h5[text()='Widgets']/../.."));
        widgets.click();
        js.executeScript("window.scrollBy(0, 300)");
        WebElement tooltips = driver.findElement(By.xpath("//span[text()='Tool Tips']"));
        tooltips.click();
        String actualResult = driver.getCurrentUrl();
        String expectedResult = "https://demoqa.com/tool-tips";
//        Expected result: You should be able to get current url  “ https://demoqa.com/tooltip/ ”
        Assert.assertEquals(actualResult, expectedResult);
    }

    /**
     * Go to 1. Go to the url: https://demoqa.com/selectable/
     * 2. Click on Item 1 on the selectable list;
     */
    @Test(priority = 5)
    void selectableTC1() {
        driver.get("https://demoqa.com/selectable/");
        WebElement item1 = driver.findElement(By.xpath("(//li[@class='mt-2 list-group-item list-group-item-action'])[1]"));
        String colorBeforeClick = item1.getCssValue("background-color");
        item1.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String colorAfterClick = item1.getCssValue("background-color");
//        The expected result should be highlighted item 1, and the rgb color code is "rgba(0, 123, 255, 1)"
        Assert.assertNotEquals(colorBeforeClick, colorAfterClick);
    }

    /***
     * 1. Go to the url: https://demoqa.com/selectable/
     * 2. Press ctrl/cmd button;
     * 3. Click on Item 1 on the selectable list;
     * 4. Click on Item 2 on the selectable list;
     * 5. Click on Item 3 on the selectable list;
     * 6. Release ctrl/cmd button
     */
    @Test(priority = 6)
    void selectableTC2() throws AWTException {
        driver.get("https://demoqa.com/selectable/");
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        Actions action = new Actions(driver);
        Action pressCtrl = action.sendKeys(Keys.CONTROL).build();
        pressCtrl.perform();
        List<WebElement> itemsList = driver.findElements(By.cssSelector("#verticalListContainer>li"));
        List<String> itemsBackgroundBeforeClick = new ArrayList<>();
        List<String> itemsBackgroundAfterClick = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            itemsBackgroundBeforeClick.add(itemsList.get(i).getCssValue("background-color"));
            itemsList.get(i).click();
            itemsBackgroundAfterClick.add(itemsList.get(i).getCssValue("background-color"));
        }
        robot.keyRelease(KeyEvent.VK_CONTROL);
//        The expected result should be highlighted items 1, 2 and 3, and the rgb color code is "rgba(0, 123, 255, 1)"
        for (int i = 0; i < itemsBackgroundBeforeClick.size(); i++) {
            Assert.assertNotEquals(itemsBackgroundBeforeClick, itemsBackgroundAfterClick);
        }
    }

    /**
     * 1. Go to the url: https://demoqa.com/selectable/
     * 2. Press ctrl/cmd button;
     * 3. Click Item 1 on the selectable list;
     * 4. Click Item 2 on the selectable list;
     * 5. Click Item 3 on the selectable list;
     * 6. Click Item 2 on the selectable list;
     * 7. Release ctrl/cmd button;
     */
    @Test(priority = 7)
    void selectableTC3() throws AWTException {
        driver.get("https://demoqa.com/selectable/");
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        Actions action = new Actions(driver);
        Action pressCtrl = action.sendKeys(Keys.CONTROL).build();
        pressCtrl.perform();
        List<WebElement> itemsList = driver.findElements(By.cssSelector("#verticalListContainer>li"));
        String item2BackgroundBeforeClick = itemsList.get(1).getCssValue("background-color");
        for (int i = 0; i < 3; i++) {
            itemsList.get(i).click();
        }
        itemsList.get(1).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String item2BackgroundAfterClick = itemsList.get(1).getCssValue("background-color");
        robot.keyRelease(KeyEvent.VK_CONTROL);
//        The expected result should be highlighted item 2, and the rgb color code is "rgba(0, 123, 255, 1)"
        Assert.assertEquals("rgba(0, 123, 255, 1)", item2BackgroundAfterClick);
    }

    /**
     * 1. Go to the url: https://demoqa.com/resizable/
     * 2. Drag a mouse from the right side of the element by 100 px to the right;
     */
    @Test(priority = 8)
    void resizableTC1() {
        driver.get("https://demoqa.com/resizable/");

//        scrolling down by 1000 pxls.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1000)"); // scrolled down by 1000 pixels

        WebElement box1 = driver.findElement(By.cssSelector("#resizable"));
        int size = box1.getSize().getWidth();
        WebElement elem1 = driver.findElement(By.cssSelector("#resizable>span"));

        Actions action = new Actions(driver);
        Action rts = action.clickAndHold(elem1).moveByOffset(100, 0).release().build();
        rts.perform();
        int size2 = box1.getSize().getWidth();
//        expected result: You should see the element is resized horizontally by 100 px
        Assert.assertTrue((size + 100) == size2);
    }

    /**
     * 1. Go to the url: https://demoqa.com/resizable/
     * 2. Drag a mouse from the bottom side of the element by 100 px down;
     */
    @Test(priority = 9)
    void resizableTC2() {
        driver.get("https://demoqa.com/resizable/");

//        scrolling down by 1000 pxls.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1000)"); // scrolled down by 1000 pixels

        WebElement box1 = driver.findElement(By.cssSelector("#resizable"));
        int size = box1.getSize().getHeight();
        WebElement elem1 = driver.findElement(By.cssSelector("#resizable>span"));

        Actions action = new Actions(driver);
        Action rts = action.clickAndHold(elem1).moveByOffset(0, 100).release().build();
        rts.perform();
        int size2 = box1.getSize().getHeight();
//        expected result: You should see the element is resized horizontally by 200 px
        Assert.assertTrue((size + 200) == size2);
    }

    /**
     * 1. Go to the url: https://demoqa.com/resizable/
     * 2. Drag a mouse diagonally left-up from the right-bottom corner of the element to its minimum size;
     */
    @Test(priority = 10)
    void resizableTC3() {
        driver.get("https://demoqa.com/resizable/");

//        scrolling down by 1000 pxls.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1000)"); // scrolled down by 1000 pixels

        WebElement box1 = driver.findElement(By.cssSelector("#resizable"));
        int size = box1.getSize().getHeight();
        WebElement elem1 = driver.findElement(By.cssSelector("#resizable>span"));

        Actions action = new Actions(driver);
        Action rts = action.clickAndHold(elem1).moveByOffset(-200, -200).release().build();
        rts.perform();
        int sizeHeight = box1.getSize().getHeight();
        int sizeWidth = box1.getSize().getWidth();
//        expected result: You should see the element is resized diagonally to the minimum size
        Assert.assertTrue(sizeHeight == 20 && sizeWidth == 20);
    }

    /**
     * 1. Go to the url: https://demoqa.com/droppable/
     * 2. Drag the element to the target;
     */
    @Test(priority = 11)
    void droppableTC() {
        driver.get("https://demoqa.com/droppable/");
        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement droppable = driver.findElement(By.id("droppable"));
        Actions action = new Actions(driver);
        Action dragAndDrop = action.dragAndDrop(draggable, droppable).build();
        dragAndDrop.perform();
        droppable = driver.findElement(By.id("droppable"));
        String colorChange = droppable.getCssValue("background-color");
        String textChange = droppable.getText();
//        expected result: After dragging the element to the target, you should see the element is within the target square
//        and the target square is colored blue with inscription „Dropped!”
        Assert.assertTrue(colorChange.equals("rgba(70, 130, 180, 1)") && textChange.equals("Dropped!"));
    }

    /**
     * 1- Go to the url:  https://demoqa.com/select-menu/
     * 2- Select one option from Select Value drop down menu
     * 3- Select one option from Select One drop down menu
     * 4- Select one option from Old Style Select Menu drop down menu
     * 5- Select options from Multiselect drop down menu
     * 6- Select one option from Standard multi select drop down menu
     */
    @Test(priority = 12)
    void selectMenu() {
        driver.get("https://demoqa.com/select-menu/");
        WebElement selectOption = driver.findElement(By.cssSelector("#withOptGroup>div>.css-1hwfws3>.css-1wa3eu0-placeholder"));

        /*couldn't select element from the list*/
    }

    /**1- Go to the url:  https://demoqa.com/menu/
     2- hover over the Main Item 2 , then sub sub list , then sub sub item 1
     */
    @Test(priority = 13)
    void Menu() {
        driver.get("https://demoqa.com/menu/");
        WebElement menu2 = driver.findElement(By.linkText("Main Item 2"));
        Actions actions = new Actions(driver);
        Action hoverOvermenu2 = actions.moveToElement(menu2).build();
        hoverOvermenu2.perform();
        WebElement subSubList = driver.findElement(By.linkText("SUB SUB LIST »"));
        Action hoverOverSubSubList = actions.moveToElement(subSubList).build();
        hoverOverSubSubList.perform();
        WebElement subSubItem1 = driver.findElement(By.linkText("Sub Sub Item 1"));
        Action hoverOverSubSubItem1 = actions.moveToElement(subSubItem1).build();
        hoverOverSubSubItem1.perform();
//        Expected result: After hover over the elements you should see the color change and other elements appeared.
        Assert.assertTrue(subSubItem1.isDisplayed());
    }

    /**1. Go to the url: https://demoqa.com/slider/
     2. Move the slider by dragging a mouse to the release point;
     */
    @Test(priority = 14)
    void moveTheSlider(){
        driver.get("https://demoqa.com/slider/");
        // Find the slider element and get its location
        WebElement slider = driver.findElement(By.className("range-slider"));
        String valueBefore = slider.getAttribute("value");

        int sliderWidth = slider.getSize().getWidth();
        int sliderX = slider.getLocation().getX();
        int sliderY = slider.getLocation().getY();
        // Calculate the pixel position of the intersection at 25 on the slider
        int intersectionPos = (int) Math.round(0.25 * sliderWidth);
        int moveToPoint = (int) Math.round(0.25 * sliderWidth);


        // Slide the slider to the right
        Actions action = new Actions(driver);

        Action moveToRight = action.moveByOffset(sliderX+intersectionPos,sliderY).clickAndHold().moveByOffset(moveToPoint,0).release().build();
        moveToRight.perform();

        String valueAfter = slider.getAttribute("value");
//        Expected result: You should see slider moves by dragging a mouse to the release point
        Assert.assertFalse(valueBefore.equals(valueAfter));
    }

    /**1. Go to the url: https://demoqa.com/slider/
     2. Move the slider by clicking on a slide bar;
     */
    @Test(priority = 15)
    void clickOnGivenPoint(){
        driver.get("https://demoqa.com/slider/");
        // Find the slider element and get its location
        WebElement slider = driver.findElement(By.className("range-slider"));
        String valueBefore = slider.getAttribute("value");
        int sliderWidth = slider.getSize().getWidth();
        int sliderX = slider.getLocation().getX();
        int sliderY = slider.getLocation().getY();
        // Calculate the pixel position of the intersection at 25 on the slider

        int moveToPoint = (int) Math.round(0.5 * sliderWidth);
        // Move the slider handle to the random position
        Actions action = new Actions(driver);

        Action clickOnPoint = action.moveByOffset(sliderX,sliderY).moveByOffset(moveToPoint,0).click().build();
        clickOnPoint.perform();
        String valueAfter = slider.getAttribute("value");
//        Expected result: You should see slider moves by clicking a bar to the click point
        Assert.assertFalse(valueBefore.equals(valueAfter));
    }

    /**1. Go to the url: https://demoqa.com/date-picker/
     2. Click on the input field;
     3. Check if the highlighted date is today’s date;
     */
    @Test(priority = 16)
    void datePickerTC1() {
        driver.get("https://demoqa.com/date-picker/");
        WebElement datePicker = driver.findElement(By.id("datePickerMonthYear"));


        LocalDate today = LocalDate.now();
        DateTimeFormatter styledDate = DateTimeFormatter.ofPattern("MMM d uuuu");
        /*current date*/
        String currentDate = today.format(styledDate);
//        System.out.println(currentDate);
        System.out.println(datePicker.getText());

        /*couldn't select element from the list*/
    }
}
