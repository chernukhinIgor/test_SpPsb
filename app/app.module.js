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
var login_1 = require("./login/login");
var register_1 = require("./register/register");
var app_component_1 = require("./app.component");
var home_component_1 = require("./home/home.component");
var edit_1 = require("./edit/edit");
var user_edit_1 = require("./user_edit/user_edit");
var not_found_component_1 = require("./not-found.component");
var task_1 = require("./task/task");
var tasks_1 = require("./tasks/tasks");
var forms_1 = require("@angular/forms");
var user_1 = require("./user/user");
var users_1 = require("./users/users");
var navbar_1 = require("./navbar/navbar");
var delete_directive_1 = require("./delete.directive");
var index_1 = require("./pagination_service/index");
var index_2 = require("./_guards/index");
var index_3 = require("./_services/index");
// определение маршрутов
var appRoutes = [
    { path: '', component: home_component_1.HomeComponent, canActivate: [index_2.AuthGuard] },
    { path: 'login', component: login_1.LoginComponent },
    { path: 'register', component: register_1.RegisterComponent },
    { path: 'task/edit/:id', component: edit_1.Edit, canActivate: [index_2.AuthGuard] },
    { path: 'task/edit', component: edit_1.Edit, canActivate: [index_2.AuthGuard] },
    { path: 'task/get/:id', component: task_1.TaskComponent, canActivate: [index_2.AuthGuard] },
    { path: 'tasks', component: tasks_1.TasksComponent, canActivate: [index_2.AuthGuard] },
    { path: 'user/edit/:id', component: user_edit_1.UserEdit, canActivate: [index_2.AuthGuard] },
    { path: 'user/edit', component: user_edit_1.UserEdit, canActivate: [index_2.AuthGuard] },
    { path: 'user/get/:id', component: user_1.UserComponent, canActivate: [index_2.AuthGuard] },
    { path: 'users', component: users_1.UsersComponent, canActivate: [index_2.AuthGuard] },
    { path: '**', component: not_found_component_1.NotFoundComponent, canActivate: [index_2.AuthGuard] }
];
var AppModule = (function () {
    function AppModule() {
    }
    return AppModule;
}());
AppModule = __decorate([
    core_1.NgModule({
        imports: [
            platform_browser_1.BrowserModule,
            router_1.RouterModule.forRoot(appRoutes),
            http_1.HttpModule,
            forms_1.FormsModule
        ],
        declarations: [
            app_component_1.AppComponent,
            home_component_1.HomeComponent,
            edit_1.Edit,
            user_edit_1.UserEdit,
            users_1.UsersComponent,
            user_1.UserComponent,
            tasks_1.TasksComponent,
            task_1.TaskComponent,
            not_found_component_1.NotFoundComponent,
            delete_directive_1.DeleteDirective,
            login_1.LoginComponent,
            register_1.RegisterComponent,
            navbar_1.myNavbar
        ],
        providers: [
            index_1.PagerService,
            index_2.AuthGuard,
            index_3.AuthenticationService,
        ],
        bootstrap: [
            app_component_1.AppComponent
        ]
    })
], AppModule);
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map