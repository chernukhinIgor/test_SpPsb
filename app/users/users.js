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
var http_service_1 = require("../http.service");
var route = './app/users/';
var UsersComponent = (function () {
    function UsersComponent(httpService) {
        this.httpService = httpService;
        this.condition = false;
    }
    UsersComponent.prototype.ngOnInit = function () {
        var _this = this;
        var options = new URLSearchParams();
        this.httpService.getData('users.json', options).subscribe(function (data) { return _this.users = data.json().data; });
    };
    UsersComponent.prototype.toggle = function () {
        this.condition = true;
    };
    return UsersComponent;
}());
UsersComponent = __decorate([
    core_1.Component({
        selector: 'users-comp',
        templateUrl: route + 'users.html',
        providers: [http_service_1.HttpService]
    }),
    __metadata("design:paramtypes", [http_service_1.HttpService])
], UsersComponent);
exports.UsersComponent = UsersComponent;
//# sourceMappingURL=users.js.map