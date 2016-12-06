package com.map;


import org.openqa.selenium.By;

public class LibraryUIMap {

 public static final By CHILDREN_LOCATOR = By.xpath("//div[@class='audience-light']//a[@href='/audiences/children']");
 public static final By CHILDREN_PAGE_HEADER = By.xpath("//div[@class='basicModuleInner']//p[1]/span");
 public static final By BOOKS_MUSIC_LOCATOR = By.xpath("//div[@class='tertiarynav']//a[contains(@href, 'audiences/children/chi-books-movies-and-music')]");
 public static final By SECOND_SECTION= By.cssSelector("div.basicModuleInner h2");
}
