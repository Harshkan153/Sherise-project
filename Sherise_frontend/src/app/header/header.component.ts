// header.component.ts
import { Component, OnInit, HostListener } from '@angular/core';
import { UserStorageService } from '../Services/storage/user-storage.service';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']

})
export class HeaderComponent implements OnInit {
  isCustomerLoggedIn: boolean = false;
  isAdminLoggedIn: boolean = false;
  showHeader: boolean = false;
   userName: string | null = null; // Corrected property declaration
     userProfilePicture: string = 'assets/images/slider/avatar-profile.svg'; // Default profile picture
      // Added variable to manage dropdown visibility
       dropdownVisible: boolean = false;


  constructor(
    private router: Router,
    private userStorageService: UserStorageService
  ) {}

  ngOnInit(): void {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.isCustomerLoggedIn = this.userStorageService.isCustomerLoggedIn();
        this.isAdminLoggedIn = this.userStorageService.isAdminLoggedIn();
        this.showHeader = !(event.url === '/login' || event.url === '/signup');

          if (this.isCustomerLoggedIn || this.isAdminLoggedIn) {
                   this.userName = this.userStorageService.getUserName(); // Get the user's name
                 }
      }
    });
  }


  // Added method to toggle dropdown visibility
  toggleDropdown(): void {
    this.dropdownVisible = !this.dropdownVisible;
  }

  logout(): void {
    this.userStorageService.signOut();
    this.router.navigateByUrl('/login');
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvents(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      const target = event.target as HTMLElement;
      if (target && target.getAttribute('role') === 'button') {
        target.click();
      }
    }
  }
}
