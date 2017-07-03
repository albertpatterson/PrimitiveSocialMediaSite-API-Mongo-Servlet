"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var mockUsers = [
    { name: 'Carl', age: 20, location: '123456', business: "Engineering", picture: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4O2Co5dqpLNFY7y9SuBbpU0J0TVBuVtyC-iVgLNtyyGj7hPJ9WIYoDqs" },
    { name: 'Kim', age: 30, location: '098767', business: "Teaching", picture: "https://uploads.scratch.mit.edu/users/avatars/1385/1878.png" },
    { name: 'Bert', age: 40, location: '789078', business: "Construction", picture: "https://www.petfinder.com/wp-content/uploads/2012/11/91615172-find-a-lump-on-cats-skin-632x475.jpg" },
    { name: 'Anne', age: 50, location: '188556', business: "Management", picture: "https://s-media-cache-ak0.pinimg.com/736x/e8/90/0a/e8900a07923cafc72c252e982163af0f.jpg" },
];
var SearchService = (function () {
    function SearchService() {
    }
    SearchService.prototype.search = function (pattern) {
        var matches = mockUsers.filter(function (u) { return u.name.match(new RegExp(pattern)); });
        return new Promise(function (r) { return setTimeout(function () { return r(matches); }, 1e3); });
    };
    return SearchService;
}());
SearchService = __decorate([
    core_1.Injectable()
], SearchService);
exports.SearchService = SearchService;
//# sourceMappingURL=search.service.js.map