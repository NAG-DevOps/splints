import { test, expect } from '@playwright/test';
import { AppPage } from './app.po';

test.describe('rest-client App', () => {
  test('should display welcome message', async ({ page }) => {
    const appPage = new AppPage(page);
    await appPage.navigateTo();
    await expect(page.locator('app-root h1')).toHaveText('Welcome to app!');
  });
});
