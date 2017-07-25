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
        // subscribe to router event
        this.activatedRoute.params.subscribe(function (params) {
            _this.id = params['id'];
            if (_this.id) {
                //let res = this.httpService.getData('/api/user/' + this.id, null);
                var res = _this.httpService.getData('user.json', null);
                _this.user = res[0];
            }
            else {
                _this.user = new classes_1.User;
            }
        });
    };
    UserEdit.prototype.onSubmit = function (f) {
        if (this.id) {
            var res = this.httpService.putData('/api/get/' + this.id, this.user);
        }
        else {
            var res = this.httpService.postData('/api/get/', this.user);
            location.href = '/task/edit/' + res.id;
        }
        console.log(this.user);
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