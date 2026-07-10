import { Page } from '@playwright/test';

export class AppPage {
  constructor(private readonly page: Page) {}

  async navigateTo(): Promise<void> {
    await this.page.goto('/');
  }

  async getParagraphText(): Promise<string> {
    return this.page.locator('app-root h1').innerText();
  }
}
