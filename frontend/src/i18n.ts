import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

import Backend from 'i18next-http-backend';
import LanguageDetector from 'i18next-browser-languagedetector';

const translationsPath = './locales/{{lng}}/{{ns}}.json';

i18n
// load translation using http -> see /public/locales (i.e. https://github.com/i18next/react-i18next/tree/master/example/react/public/locales)
// learn more: https://github.com/i18next/i18next-http-backend
.use(Backend)
// detect user language
// learn more: https://github.com/i18next/i18next-browser-languageDetector
.use(LanguageDetector)
// pass the i18n instance to react-i18next.
.use(initReactI18next)
// init i18next
// for all options read: https://www.i18next.com/overview/configuration-options
.init({
  lng: 'en',
  fallbackLng: 'en',
  supportedLngs: ['en', 'da'],
  debug: false,
  defaultNS: 'common',
  interpolation: {
    escapeValue: false,
  },
  backend: {
    loadPath: translationsPath,
  },
  detection: {
    order: ['htmlTag', 'cookie', 'localStorage', 'sessionStorage', 'querystring', 'navigator'],
    caches: ['cookie', 'localStorage'],
  },
});

export default i18n;