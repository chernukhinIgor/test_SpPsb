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
var http_service_1 = require("./http.service");
var DeleteDirective = (function () {
    function DeleteDirective(httpService) {
        this.httpService = httpService;
        this.deleteClick = new core_1.EventEmitter();
    }
    DeleteDirective.prototype.ngOnInit = function () {
    };
    DeleteDirective.prototype.onClick = function () {
        console.log(this.moduleName, this.entityId);
        if (this.entityId) {
            var res = this.httpService.deleteData('/api/delete/' + this.entityId, this.moduleName);
        }
        else {
            alert('Invalid id');
        }
    };
    return DeleteDirective;
}());
__decorate([
    core_1.Input(),
    __metadata("design:type", String)
], DeleteDirective.prototype, "moduleName", void 0);
__decorate([
    core_1.Input(),
    __metadata("design:type", Number)
], DeleteDirective.prototype, "entityId", void 0);
__decorate([
    core_1.Output(),
    __metadata("design:type", core_1.EventEmitter)
], DeleteDirective.prototype, "deleteClick", void 0);
__decorate([
    core_1.HostListener('click', ['$event']),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", []),
    __metadata("design:returntype", void 0)
], DeleteDirective.prototype, "onClick", null);
DeleteDirective = __decorate([
    core_1.Directive({
        selector: '[deleteClick]'
    }),
    __metadata("design:paramtypes", [http_service_1.HttpService])
], DeleteDirective);
exports.DeleteDirective = DeleteDirective;
//# sourceMappingURL=delete.directive.js.map