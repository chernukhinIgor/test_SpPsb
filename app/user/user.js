"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var http_service_1 = require("../http.service");
var platform_browser_1 = require("@angular/platform-browser");
var route = './app/user/';
var UserComponent = (function () {
    function UserComponent(activatedRoute, httpService) {
        this.activatedRoute = activatedRoute;
        this.httpService = httpService;
    }
    UserComponent.prototype.ngOnInit = function () {
        var _this = this;
        var title = new platform_browser_1.Title('');
        title.setTitle('View User');
        var options = new URLSearchParams();
        this.activatedRoute.params.subscribe(function (params) {
            options.set('id', params['id']);
        });
        this.httpService.getData('user.json', options).subscribe(function (data) { return _this.user = data.json().data[0]; });
        this.httpService.getData('tasksFromUsers.json', options).subscribe(function (data) { return _this.tasks = data.json().data; });
    };
    return UserComponent;
}());
UserComponent = __decorate([
    core_1.Component({
        selector: 'user-comp',
        templateUrl: route + 'user.html',
        providers: [http_service_1.HttpService]
    }),
    __metadata("design:paramtypes", [router_1.ActivatedRoute, http_service_1.HttpService])
], UserComponent);
exports.UserComponent = UserComponent;
//# sourceMappingURL=user.js.map