import { test, expect } from '@playwright/test';

test('has title', async ({ page }) => {
  await page.goto('https://qecore.io/');

  // Expect a title "to contain" a substring.
  await expect(page).toHaveTitle(/Yum Brands QE Core Team/);
});

test('get GraphQL Monitor link', async ({ page }) => {
  await page.goto('https://qecore.io/');

  // Click the learn more link.
  await page.getByRole('link', { name: 'GraphQL Monitor' }).click();

  // Expects page to have a heading with the name.
  const firstHeading = page.getByRole('heading').first();
  await expect(firstHeading).toBeVisible();
  await expect(firstHeading).toHaveText('GraphQL Query Monitor');
});
