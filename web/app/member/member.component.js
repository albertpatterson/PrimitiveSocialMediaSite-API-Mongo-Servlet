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
var auth_service_1 = require("./../services/auth.service");
var message_service_1 = require("./services/message.service");
var MemberComponent = (function () {
    function MemberComponent(authService, router, messageService, activatedRoute) {
        this.authService = authService;
        this.router = router;
        this.messageService = messageService;
        this.activatedRoute = activatedRoute;
        this.searchPattern = '';
    }
    MemberComponent.prototype._setMessageCount = function () {
        return this.messageService.getMessageCount()
            .then(function (messageCount) {
            this.messageCount = messageCount;
        }.bind(this));
    };
    MemberComponent.prototype.search = function () {
        this.router.navigate(['member', this.username, 'search', this.searchPattern]);
    };
    MemberComponent.prototype.signout = function () {
        this.authService.signout()
            .then(function (signedOut) {
            this.router.navigate(['/sign-in']);
        }.bind(this));
    };
    MemberComponent.prototype.ngOnInit = function () {
        this.activatedRoute.params
            .subscribe(function (params) {
            console.log(params);
            this.username = params["ownName"];
            console.log('username', this.username);
            this.authService.assertLoggedIn(this.username)
                .then(this._setMessageCount.bind(this))
                .catch(function (e) { return console.log(e); });
            // check login
        }.bind(this));
    };
    return MemberComponent;
}());
MemberComponent = __decorate([
    core_1.Component({
        templateUrl: './member.component.html',
        styleUrls: ['./member.component.css']
    }),
    __metadata("design:paramtypes", [auth_service_1.AuthService,
        router_1.Router,
        message_service_1.MessageService,
        router_1.ActivatedRoute])
], MemberComponent);
exports.MemberComponent = MemberComponent;
//# sourceMappingURL=member.component.js.map