"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var mockPremiumItems = ["http://cdn3-www.dogtime.com/assets/uploads/gallery/30-impossibly-cute-puppies/impossibly-cute-puppy-21.jpg",
    "http://cdn2-www.dogtime.com/assets/uploads/gallery/30-impossibly-cute-puppies/impossibly-cute-puppy-8.jpg",
    "http://cdn1-www.dogtime.com/assets/uploads/gallery/30-impossibly-cute-puppies/impossibly-cute-puppy-2.jpg",
    "http://cdn3-www.dogtime.com/assets/uploads/gallery/30-impossibly-cute-puppies/impossibly-cute-puppy-5.jpg",
    "https://www.cesarsway.com/sites/newcesarsway/files/styles/large_article_preview/public/All-about-puppies--Cesar%E2%80%99s-tips%2C-tricks-and-advice.jpg?itok=bi9xUvwe"];
var PremiumService = (function () {
    function PremiumService() {
    }
    PremiumService.prototype.getPremiumItems = function () {
        // return Promise.resolve(mockPremiumItems);
        return new Promise(function (r) { return setTimeout(function () { return r(mockPremiumItems); }, 1e3); });
    };
    return PremiumService;
}());
PremiumService = __decorate([
    core_1.Injectable()
], PremiumService);
exports.PremiumService = PremiumService;
//# sourceMappingURL=premium.service.js.map