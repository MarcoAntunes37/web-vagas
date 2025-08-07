import { Injectable } from '@angular/core';
@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  setTheme(theme: 'light' | 'dark') {
    document.body.classList.toggle('dark', theme === 'dark');
  }

  detectBrowserTheme(): 'light' | 'dark' {
    return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
  }

  applyTheme() {
    const browserTheme = this.detectBrowserTheme();
    this.setTheme(browserTheme);
  }
}