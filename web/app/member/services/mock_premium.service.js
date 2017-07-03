"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var mockUserData_1 = require("../../mockData/mockUserData");
var PremiumService = (function () {
    function PremiumService() {
    }
    PremiumService.prototype.getPremiumItems = function (username) {
        // return Promise.resolve(mockPremiumItems);
        return new Promise(function (r) { return setTimeout(function () { return r(mockUserData_1.getPremuimContent(username)); }, 1e3); });
    };
    return PremiumService;
}());
PremiumService = __decorate([
    core_1.Injectable()
], PremiumService);
exports.PremiumService = PremiumService;
//# sourceMappingURL=mock_premium.service.js.map