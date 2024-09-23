import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';


@Component({
selector: 'app-home',
templateUrl: './home.component.html',
styleUrl: './home.component.css'
})
export class HomeComponent  implements OnInit {

constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  loadWatsonAssistant(): void {
    if (isPlatformBrowser(this.platformId)) {
      window.watsonAssistantChatOptions = {
        integrationID: "6ab45a9f-e67d-4917-ad49-b0c6357e05fb", // Your integration ID
        region: "au-syd", // The region your integration is hosted in
        serviceInstanceID: "308ee3dd-a2f9-46a7-8c42-298376ac0c4f", // Your service instance ID
        onLoad: async (instance: any) => {
          await instance.render();
        }
      };
      const script = document.createElement('script');
      script.src = "https://web-chat.global.assistant.watson.appdomain.cloud/versions/latest/WatsonAssistantChatEntry.js";
      document.head.appendChild(script);
    }
  }

  ngOnInit() {
    this.loadWatsonAssistant();
  }
}
