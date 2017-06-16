import { EsseEuJaLiUIPage } from './app.po';

describe('esse-eu-ja-li-ui App', () => {
  let page: EsseEuJaLiUIPage;

  beforeEach(() => {
    page = new EsseEuJaLiUIPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
