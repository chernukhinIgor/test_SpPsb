(function (global) {
    System.config({
        paths: {
            // псевдоним для пути к модулям
            'npm:': 'node_modules/',
            'underscore': 'node_modules/underscore'
        },
        // указываем загрузчику System, где искать модули
        map: {
            // наше приложение будет находиться в папке app
            app: 'app',
            'tslib': 'npm:tslib/tslib.js',
            // пакеты angular
            '@angular/core': 'npm:@angular/core/bundles/core.umd.js',
            '@angular/common': 'npm:@angular/common/bundles/common.umd.js',
            '@angular/compiler': 'npm:@angular/compiler/bundles/compiler.umd.js',
            '@angular/platform-browser': 'npm:@angular/platform-browser/bundles/platform-browser.umd.js',
            '@angular/platform-browser-dynamic': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
            '@angular/http': 'npm:@angular/http/bundles/http.umd.js',
            '@angular/router': 'npm:@angular/router/bundles/router.umd.js',
            '@angular/animations': 'npm:@angular/animations/bundles/animations.umd.js',
            '@angular/animations/browser': 'npm:@angular/animations/bundles/animations-browser.umd.js',
            '@angular/platform-browser/animations': 'npm:@angular/platform-browser/bundles/platform-browser-animations.umd.js',
            '@angular/forms': 'npm:@angular/forms/bundles/forms.umd.js',

            '@progress/kendo-angular-buttons': 'npm:@progress/kendo-angular-buttons',
            '@progress/kendo-angular-l10n': 'npm:@progress/kendo-angular-l10n',
            '@progress/kendo-angular-popup': 'npm:@progress/kendo-angular-popup',
            '@progress/kendo-angular-layout': 'npm:@progress/kendo-angular-layout',
            '@progress/kendo-angular-dropdowns': 'npm:@progress/kendo-angular-dropdowns',
            '@progress/kendo-angular-inputs': 'npm:@progress/kendo-angular-inputs',
            '@progress/kendo-data-query': 'npm:@progress/kendo-data-query',
            '@progress/kendo-angular-intl': 'npm:@progress/kendo-angular-intl',
            '@progress/kendo-angular-dialog': 'npm:@progress/kendo-angular-dialog',
            '@progress/kendo-angular-excel-export': 'npm:@progress/kendo-angular-excel-export',
            '@progress/kendo-drawing': 'npm:@progress/kendo-drawing',
            '@progress/kendo-popup-common': 'npm:@progress/kendo-popup-common',
            '@progress/kendo-theme-default': 'npm:@progress/kendo-theme-default',
            // остальные пакеты
            'rxjs': 'npm:rxjs',
            'angular-in-memory-web-api': 'npm:angular-in-memory-web-api/bundles/in-memory-web-api.umd.js',
            'material-design-icons': 'npm:material-design-icons'
        },
        // пакеты, которые указывают загрузчику System, как загружать файлы без имени и расширения
        packages: {
            app: {
                main: './main.js',
                defaultExtension: 'js'
            },
            rxjs: {
                defaultExtension: 'js'
            },
            'underscore': {
                main: 'underscore.js',
                defaultExtension: 'js'
            },
            '@progress/kendo-angular-buttons': {
                main: 'dist/cdn/js/kendo-angular-buttons.js',
                defaultExtension: 'js'
            },
            '@progress/kendo-angular-l10n': {
                main: 'dist/cdn/js/kendo-angular-l10n.js',
                defaultExtension: 'js'
            },
            '@progress/kendo-angular-layout': {
                'main': 'dist/cdn/js/kendo-angular-layout.js',
                'defaultExtension': 'js'
            },
            '@progress/kendo-angular-dialog': {
                'main': 'dist/cdn/js/kendo-angular-dialog.js',
                'defaultExtension': 'js'
            },
            '@progress/kendo-angular-popup': {
                'main': 'dist/cdn/js/kendo-angular-popup.js',
                'defaultExtension': 'js'
            }
        }
    });
})(this);
