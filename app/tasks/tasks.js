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
var route = './app/tasks/';
var Task = (function () {
    function Task() {
    }
    return Task;
}());
exports.Task = Task;
var TasksComponent = (function () {
    function TasksComponent(httpService) {
        this.httpService = httpService;
        this.condition = false;
    }
    TasksComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.httpService.getData('task.json').subscribe(function (data) { return _this.tasks = data.json().data; });
    };
    TasksComponent.prototype.toggle = function () {
        this.condition = true;
    };
    return TasksComponent;
}());
TasksComponent = __decorate([
    core_1.Component({
        selector: 'form-app',
        templateUrl: route + 'tasks.html',
        providers: [http_service_1.HttpService]
    }),
    __metadata("design:paramtypes", [http_service_1.HttpService])
], TasksComponent);
exports.TasksComponent = TasksComponent;
//# sourceMappingURL=tasks.js.map