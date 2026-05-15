import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

export interface Language {
  code: string;
  label: string;
}

const LANGUAGES: Language[] = [
  { code: 'es', label: 'ES' },
  { code: 'en', label: 'EN' },
  { code: 'fr', label: 'FR' },
  { code: 'pt', label: 'PT' }
];

const STORAGE_KEY = 'app_language';

@Injectable({ providedIn: 'root' })
export class LanguageService {

  constructor(private translate: TranslateService) {}

  init(): void {
    const saved = localStorage.getItem(STORAGE_KEY) ?? 'es';
    this.translate.use(saved);
  }

  getCurrent(): Language {
    const code = this.translate.currentLang ?? 'es';
    return LANGUAGES.find(l => l.code === code) ?? LANGUAGES[0];
  }

  getAvailable(): Language[] {
    const current = this.getCurrent().code;
    return LANGUAGES.filter(l => l.code !== current);
  }

  change(code: string): void {
    this.translate.use(code);
    localStorage.setItem(STORAGE_KEY, code);
  }
}
