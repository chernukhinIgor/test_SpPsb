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
var route = './app/task/';
var TaskComponent = (function () {
    function TaskComponent(activatedRoute, httpService) {
        this.activatedRoute = activatedRoute;
        this.httpService = httpService;
    }
    TaskComponent.prototype.ngOnInit = function () {
        var _this = this;
        var title = new platform_browser_1.Title('');
        title.setTitle('View Task');
        var options = new URLSearchParams();
        this.activatedRoute.params.subscribe(function (params) {
            options.set('id', params['id']);
            _this.httpService.getData('task/' + params['id'], null).subscribe(function (data) {
                if (data.json().success == true) {
                    _this.task = data.json().data[0];
                }
                else {
                    alert(data.json().message);
                }
            });
        });
    };
    return TaskComponent;
}());
TaskComponent = __decorate([
    core_1.Component({
        selector: 'task-comp',
        templateUrl: route + 'task.html',
        providers: [http_service_1.HttpService]
    }),
    __metadata("design:paramtypes", [router_1.ActivatedRoute, http_service_1.HttpService])
], TaskComponent);
exports.TaskComponent = TaskComponent;
//# sourceMappingURL=task.js.map