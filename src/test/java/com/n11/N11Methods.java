package com.n11;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class N11Methods {

    public WebDriver driver;
    public String productName;

    public void n11WebPageTestCases() {

        try {
            openChromeBrowser();
            gotoN11Page();
            clickLogin();
            enterUsernameAndPassword();
            searchProduct();
            controlSearchedProduct();
            gotoSecondPage();
            getThirdProductName();
            addThirdProductToFavourite();
            gotoFavouriteMainPageMenu();
            gotoMyFavouriteProduct();
            confirmSameProduct();
            deleteProductFromFavourite();
            controlDeletedOrNot();
            closeDeleteConfirmMessagePopup();

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            // browser kapatmak için
            if (driver != null)
                driver.quit();
            // driver.close();
        }
    }

    private void closeDeleteConfirmMessagePopup() {
        driver.findElement(By.xpath("//span[@class='btn btnBlack confirm']")).click();
    }

    private void controlDeletedOrNot() {
        WebElement checkProductDelete = driver.findElement(By.xpath("//span[text()='Ürününüz listeden silindi.']"));
        if (checkProductDelete != null) {
            System.out.println("Favori listesinde ürün bulunmamaktadır");
            System.out.println("-----------------------------");
        } else {
            System.out.println("İstediğiniz ürün silinemedi");
            System.out.println("-----------------------------");
        }
    }

    private void deleteProductFromFavourite() {
        driver.findElement(By.xpath("//span[@class='deleteProFromFavorites']")).click();
    }

    private void confirmSameProduct() {
        String favouriteProductName = driver.findElement(By.xpath("//h3[@class='productName bold']")).getText();
        System.out.println(favouriteProductName);
        if (favouriteProductName.equals(productName)) {
            System.out.println("istenilen urun favoriye eklendi");
            System.out.println("-----------------------------");
        } else {
            System.out.println("Yanlış urun favoriye eklendi");
            System.out.println("-----------------------------");
        }
    }

    private void gotoMyFavouriteProduct() {
        driver.findElement(By.xpath("//a[@href='https://www.n11.com/hesabim/favorilerim']")).click();
    }

    private void gotoFavouriteMainPageMenu() {
        //muose-cursor "hesabım" linkine goturup mouse over yaptiriyorum
        WebElement hesabim = driver.findElement(By.xpath("//a[text()='Hesabım']"));
        Actions builder = new Actions(driver);
        builder.moveToElement(hesabim).build().perform();
        //Sonrasinda Favorilerim tiklaniyor
        driver.findElement(By.xpath("//a[text()='İstek Listem / Favorilerim']")).click();
    }

    private void addThirdProductToFavourite() {
        driver.findElement(By.xpath("//div[@id='view']/ul/li[3]//span[contains(@class,'followBtn')]")).click();
    }

    private void getThirdProductName() {
        productName = driver.findElement(By.xpath("//div[@id='view']/ul/li[3]//h3[@class='productName bold']")).getText();
        System.out.println("seçilen 3. ürünün ismi: " + productName);
        System.out.println("-----------------------------");
    }

    private void gotoSecondPage() {
        //pagination uzerindeki "pagination" class'ina sahip "div" elementi altindaki 2. sayfaya git
        driver.findElement(By.xpath("(//div[@class='pagination'])/a[contains(text(),'2')]")).click();
        if (driver.getCurrentUrl().contains("pg=2")) {
            System.out.println("Sayfaya 2 ye geçiş yaptınız");
            System.out.println("-----------------------------");

        } else {
            System.out.println("2. sayfaya gidemediniz");
            System.out.println("-----------------------------");
        }
    }

    private void controlSearchedProduct() {
        if (driver.getCurrentUrl().contains("arama") || driver.getCurrentUrl().contains("samsung")) {
            System.out.println("İstenilen arama hakkında sonuçlar bulundu");
            System.out.println("-----------------------------");
        } else {
            System.out.println("arama başarısızı oldu");
            System.out.println("-----------------------------");
        }
    }

    private void searchProduct() {
        driver.findElement(By.id("searchData")).sendKeys(N11Constant.urunAdi);
        driver.findElement(By.className("searchBtn")).click();
    }

    private void enterUsernameAndPassword() {
        driver.findElement(By.id("email")).sendKeys("mehmetbesli063@gmail.com");
        driver.findElement(By.id("password")).sendKeys("mhmtbsln1163");
        driver.findElement(By.id("loginButton")).click();
    }

    private void clickLogin() {
        driver.findElement(By.className("btnSignIn")).click();
        controlLoginPage();
    }

    private void controlLoginPage() {
        if (driver.getCurrentUrl().contains("giris-yap")) {
            System.out.println("Login sayfasındasınız");
            System.out.println("-----------------------------");
        } else {
            System.out.println("istenilen sayfaya gidemediniz");
            System.out.println("-----------------------------");
        }
    }

    private void openChromeBrowser() {
        String path = System.getProperty("user.dir");
        System.out.println("Proje path : " + path);
        System.setProperty("webdriver.chrome.driver", path + "\\lib\\chromedriver.exe");

        driver = new ChromeDriver();

        // tum elementler icin maksimum 15 bekleyeck
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    private void gotoN11Page() {
        driver.get("https://www.n11.com/");
        if (driver.getCurrentUrl().contains("https://www.n11.com/")) {
            System.out.println("N11 sayfasina gidildi");
        } else {
            throw new WebDriverException("ilgili sayfaya acilamadi");
        }
    }
}
