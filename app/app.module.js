"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var platform_browser_1 = require("@angular/platform-browser");
var router_1 = require("@angular/router");
var http_1 = require("@angular/http");
var app_component_1 = require("./app.component");
var home_component_1 = require("./home/home.component");
var edit_1 = require("./edit/edit");
var user_edit_1 = require("./user_edit/user_edit");
var not_found_component_1 = require("./not-found.component");
var task_1 = require("./task/task");
var tasks_1 = require("./tasks/tasks");
var forms_1 = require("@angular/forms");
// определение маршрутов
var appRoutes = [
    { path: '', component: home_component_1.HomeComponent },
    { path: 'task/edit/:id', component: edit_1.Edit },
    { path: 'task/edit', component: edit_1.Edit },
    { path: 'task/get/:id', component: task_1.TaskComponent },
    { path: 'tasks', component: tasks_1.TasksComponent },
    { path: 'user/edit/:id', component: user_edit_1.UserEdit },
    { path: 'user/edit', component: user_edit_1.UserEdit },
    { path: '**', component: not_found_component_1.NotFoundComponent }
];
var AppModule = (function () {
    function AppModule() {
    }
    return AppModule;
}());
AppModule = __decorate([
    core_1.NgModule({
        imports: [platform_browser_1.BrowserModule, router_1.RouterModule.forRoot(appRoutes), http_1.HttpModule, forms_1.FormsModule],
        declarations: [app_component_1.AppComponent, home_component_1.HomeComponent, edit_1.Edit, user_edit_1.UserEdit, tasks_1.TasksComponent, task_1.TaskComponent, not_found_component_1.NotFoundComponent],
        bootstrap: [app_component_1.AppComponent]
    })
], AppModule);
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map