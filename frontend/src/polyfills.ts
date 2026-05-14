/**
 * This file includes polyfills needed by Angular and is loaded before the app.
 * You can add your own extra polyfills to this file if the ones in node_modules
 * do not cover what you need.
 *
 * This file is divided into 2 sections:
 *   1. Browser polyfills. These are applied before loading ZoneJS and are sorted by browsers.
 *   2. Application imports. Files imported after ZoneJS that should be loaded before your main
 *      file.
 *
 * The current setup is for so-called "evergreen" browsers; the code falls back to `ngLocalize`
 * when needed (either automatically or as fallback on IE11 `noModule`).
 *
 * Learn more in https://angular.io/guide/browser-support
 */

/***************************************************************************************************
 * BROWSER POLYFILLS
 */

/**
 * By default, zone.js will patch all possible macroTask and (some) micro task zones.
 * Users can provide their own custom zone.js patches by uncommenting the following
 * lines before importing zone.js, or provide their own Zone.run callback.
 * (used to disable patch certain stack, see `zone.flags.ts`)
 *
 * import { Zone } from 'zone.js';
 * Object.defineProperty(Zone, 'current', { get: () => new NgZone({}) });
 */

/***************************************************************************************************
 * Zone JS is required by default for Angular itself.
 */
// Included with Angular CLI.

/***************************************************************************************************
 * APPLICATION IMPORTS
 */

/**
 * i18n support: This import is required for Angular to support the i18n system.
 * Without this import, Angular will throw an error when it encounters i18n markers.
 */
import '@angular/localize/init';
