import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../customer.service';
import Swal from 'sweetalert2';  // Import SweetAlert

@Component({
selector: 'app-my-orders',
templateUrl: './my-orders.component.html',
styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {
myOrders: any;

// Define the status mapping with an index signature
statusMapper: { [key: string]: string } = {
'Placed': 'label-primary',
'Shipped': 'label-info',
'Delivered': 'label-success',
'Cancelled': 'label-danger'
};

constructor(private customerService: CustomerService) {}

  ngOnInit(): void {
    this.getMyOrders();
  }

  getMyOrders(): void {
    this.customerService.getOrdersByUserId().subscribe({
      next: (res) => {
        this.myOrders = res;
      },
      error: (err) => {
        console.error('Failed to load orders:', err);
      }
    });
  }

  getStatusClass(status: string): string {
    return this.statusMapper[status] || 'label-default';
  }

  // New method to cancel an order using SweetAlert for confirmation
  cancelOrder(orderId: number): void {
    Swal.fire({
      title: 'Are you sure?',
      text: 'Do you want to cancel this order?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, cancel it!'
    }).then((result) => {
      if (result.isConfirmed) {
        // Proceed with the cancellation
        this.customerService.cancelOrder(orderId, 'Cancelled').subscribe({
          next: (res) => {
            Swal.fire(
              'Cancelled!',
              'Your order has been cancelled.',
              'success'
            );
            this.getMyOrders(); // Refresh orders after cancellation
          },
          error: (err) => {
            Swal.fire(
              'Failed!',
              'There was a problem cancelling your order.',
              'error'
            );
            console.error('Cancellation error:', err);
          }
        });
      }
    });
  }
}
