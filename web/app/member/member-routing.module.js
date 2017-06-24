"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var member_component_1 = require("./member.component");
var home_component_1 = require("./home/home.component");
var messages_component_1 = require("./messages/messages.component");
var other_component_1 = require("./other/other.component");
var search_component_1 = require("./search/search.component");
var premium_component_1 = require("./premium/premium.component");
var routes = [
    { path: 'member',
        component: member_component_1.MemberComponent,
        children: [
            { path: 'home', component: home_component_1.HomeComponent },
            { path: 'other/:name', component: other_component_1.OtherComponent },
            { path: 'messages', component: messages_component_1.MessagesComponent },
            { path: 'search/:pattern', component: search_component_1.SearchComponent },
            { path: 'premium', component: premium_component_1.PremiumComponent }
        ] }
];
var MemberRoutingModule = (function () {
    function MemberRoutingModule() {
    }
    return MemberRoutingModule;
}());
MemberRoutingModule = __decorate([
    core_1.NgModule({
        imports: [router_1.RouterModule.forChild(routes)],
        exports: [router_1.RouterModule]
    })
], MemberRoutingModule);
exports.MemberRoutingModule = MemberRoutingModule;
;
//# sourceMappingURL=member-routing.module.js.map