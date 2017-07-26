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
var classes_1 = require("../classes");
var platform_browser_1 = require("@angular/platform-browser");
var element = 'user_edit';
var route = './app/' + element + '/';
var UserEdit = (function () {
    function UserEdit(router, activatedRoute, httpService) {
        this.router = router;
        this.activatedRoute = activatedRoute;
        this.httpService = httpService;
    }
    UserEdit.prototype.ngOnInit = function () {
        var _this = this;
        this.activatedRoute.params.subscribe(function (params) {
            _this.id = params['id'];
            var title = new platform_browser_1.Title('');
            if (_this.id) {
                title.setTitle('Edit User');
                _this.httpService.getData('user/' + _this.id, null).subscribe(function (data) {
                    if (data.json().success == true) {
                        _this.user = data.json().data[0];
                    }
                    else {
                        alert(data.json().message);
                    }
                });
            }
            else {
                title.setTitle('Create User');
                _this.user = new classes_1.User;
            }
        });
    };
    UserEdit.prototype.onSubmit = function () {
        var _this = this;
        if (this.id) {
            this.httpService.putData('user', this.user).subscribe(function (data) {
                if (data.json().success == true) {
                    location.href = '/task/get/' + _this.id;
                }
                else {
                    alert(data.json().message);
                }
            });
        }
        else {
            this.httpService.postData('user', this.user).subscribe(function (data) {
                if (data.json().success == true) {
                    location.href = '/task/get/' + data.json().data;
                }
                else {
                    alert(data.json().message);
                }
            });
        }
    };
    UserEdit.prototype.cancel = function () {
        window.history.back();
    };
    return UserEdit;
}());
UserEdit = __decorate([
    core_1.Component({
        selector: 'home-app',
        templateUrl: route + element + '.html',
        providers: [http_service_1.HttpService]
    }),
    __metadata("design:paramtypes", [router_1.Router, router_1.ActivatedRoute, http_service_1.HttpService])
], UserEdit);
exports.UserEdit = UserEdit;
//# sourceMappingURL=user_edit.js.map