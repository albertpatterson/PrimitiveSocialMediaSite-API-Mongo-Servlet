"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var common_1 = require("@angular/common");
var forms_1 = require("@angular/forms");
var member_routing_module_1 = require("./member-routing.module");
var member_component_1 = require("./member.component");
var home_component_1 = require("./home/home.component");
var messages_component_1 = require("./messages/messages.component");
var other_component_1 = require("./other/other.component");
var search_component_1 = require("./search/search.component");
var premium_component_1 = require("./premium/premium.component");
var message_form_component_1 = require("./utils/messageForm/message-form.component");
var auth_service_1 = require("./../services/auth.service");
var message_service_1 = require("./services/message.service");
var post_service_1 = require("./services/post.service");
// import {SearchService} from './services/search.service';
var premium_service_1 = require("./services/premium.service");
var subscription_service_1 = require("./services/subscription.service");
// import {AuthService} from './../services/mock_auth.service'
// import {MessageService} from './services/mock_message.service';
// import {PostService} from './services/mock_post.service';
var mock_search_service_1 = require("./services/mock_search.service");
// import {PremiumService} from './services/mock_premium.service';
var MemberModule = (function () {
    function MemberModule() {
    }
    return MemberModule;
}());
MemberModule = __decorate([
    core_1.NgModule({
        imports: [member_routing_module_1.MemberRoutingModule,
            common_1.CommonModule,
            forms_1.FormsModule],
        declarations: [member_component_1.MemberComponent,
            home_component_1.HomeComponent,
            messages_component_1.MessagesComponent,
            other_component_1.OtherComponent,
            search_component_1.SearchComponent,
            premium_component_1.PremiumComponent,
            message_form_component_1.MessageFormComponent],
        providers: [auth_service_1.AuthService,
            message_service_1.MessageService,
            post_service_1.PostService,
            mock_search_service_1.SearchService,
            premium_service_1.PremiumService,
            subscription_service_1.SubscriptionService]
    })
], MemberModule);
exports.MemberModule = MemberModule;
;
//# sourceMappingURL=member.module.js.map