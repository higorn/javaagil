import { EsseEuJaLiUIPage } from './app.po';

describe('For name nicanor', () => {
  let page: EsseEuJaLiUIPage;

  beforeEach(() => {
    page = new EsseEuJaLiUIPage();
  });

  it('should login', () => {
    page.navigateTo();
    expect(page.getLoginTitle()).toEqual('Login');
    page.getLoginUserInput().sendKeys('nicanor');
    page.getLoginPasswordInput().sendKeys('abc');
    page.getLoginButton().click();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  })

  it('should logout', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
    page.getLogoutButton().click();
    expect(page.getLoginTitle()).toEqual('Login');
  });
});
