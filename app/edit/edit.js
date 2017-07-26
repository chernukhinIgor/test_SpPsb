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
var element = 'edit';
var route = './app/' + element + '/';
//tmp variables
var users = [
    {
        id: '1',
        name: 'name One'
    },
    {
        id: '2',
        name: 'name two'
    },
    {
        id: '3',
        name: 'name three'
    },
    {
        id: '3',
        name: 'name four'
    }
];
var Edit = (function () {
    function Edit(router, activatedRoute, httpService) {
        this.router = router;
        this.activatedRoute = activatedRoute;
        this.httpService = httpService;
    }
    Edit.prototype.ngOnInit = function () {
        var _this = this;
        this.activatedRoute.params.subscribe(function (params) {
            var title = new platform_browser_1.Title('');
            _this.getUsers();
            _this.id = params['id'];
            if (_this.id) {
                title.setTitle('Edit Task');
                //let res = this.httpService.getData('/api/get/' + this.id, null);
                _this.httpService.getData('task/' + _this.id, null).subscribe(function (data) { return _this.task = data.json().data[0]; });
            }
            else {
                title.setTitle('Create Task');
                _this.task = new classes_1.Task;
            }
        });
    };
    Edit.prototype.onSubmit = function () {
        if (this.id) {
            var res = this.httpService.putData('task/', this.task);
        }
        else {
            var res = this.httpService.postData('task/', this.task).subscribe(function (data) {
                if (data.json().success == true) {
                    // console.log(data.json().data.userId)
                    location.href = '/task/get/' + data.json().data.userId;
                }
                else {
                    alert(data.json().message);
                }
            });
        }
    };
    Edit.prototype.getUsers = function () {
        var _this = this;
        var options = new URLSearchParams();
        return this.httpService.getData('users?params=id&params=name', options).subscribe(function (data) {
            if (data.json().success == true) {
                _this.users = data.json().data;
            }
            else {
                alert(data.json().message);
            }
        });
    };
    Edit.prototype.cancel = function () {
        window.history.back();
    };
    return Edit;
}());
Edit = __decorate([
    core_1.Component({
        selector: 'home-app',
        templateUrl: route + element + '.html',
        providers: [http_service_1.HttpService]
    }),
    __metadata("design:paramtypes", [router_1.Router, router_1.ActivatedRoute, http_service_1.HttpService])
], Edit);
exports.Edit = Edit;
//# sourceMappingURL=edit.js.map