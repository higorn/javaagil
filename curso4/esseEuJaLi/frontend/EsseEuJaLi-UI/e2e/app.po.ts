import { browser, by, element } from 'protractor';

export class EsseEuJaLiUIPage {
  navigateTo() {
    return browser.get('/');
  }

  getLoginTitle() {
    return element(by.css('md-card-title')).getText();
  }

  getLoginUserInput() {
    return element(by.css('#inputUser'));
  }

  getParagraphText() {
    return element(by.css('app-root h1')).getText();
  }

  getLoginPasswordInput() {
    return element(by.css('#inputPassword'));
  }

  getLoginButton() {
    return element(by.css('.mat-button'));
  }

  getLogoutButton() {
    return element(by.css('.mat-button'));
  }
}
