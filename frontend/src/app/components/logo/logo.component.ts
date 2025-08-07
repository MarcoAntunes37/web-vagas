import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ThemeService } from '../../service/theme/theme.service';

@Component({
  selector: 'app-logo',
  imports: [CommonModule],
  templateUrl: './logo.component.html',
  styleUrl: './logo.component.scss'
})
export class LogoComponent {
  isDarkMode: boolean = false;
  private themeService: ThemeService;
  constructor(themeService: ThemeService) {
    this.themeService = themeService;
  }

  ngOnInit() {
    this.isDarkMode = this.themeService.getTheme() === 'dark';
  }
}
