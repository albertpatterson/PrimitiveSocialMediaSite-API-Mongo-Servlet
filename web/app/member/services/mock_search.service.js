"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var mockUserData_1 = require("../../mockData/mockUserData");
var SearchService = (function () {
    function SearchService() {
    }
    SearchService.prototype.search = function (pattern) {
        var searchRegExp = new RegExp(pattern);
        var matches = [];
        console.log('mud', mockUserData_1.mockUserData);
        console.log('pattern', pattern);
        for (var name_1 in mockUserData_1.mockUserData) {
            var user = mockUserData_1.mockUserData[name_1];
            console.log('user', user);
            if (user.name.match(searchRegExp)) {
                matches.push(user);
            }
        }
        console.log('matches', matches);
        return new Promise(function (r) { return setTimeout(function () { return r(matches); }, 1e3); });
    };
    return SearchService;
}());
SearchService = __decorate([
    core_1.Injectable()
], SearchService);
exports.SearchService = SearchService;
//# sourceMappingURL=mock_search.service.js.map