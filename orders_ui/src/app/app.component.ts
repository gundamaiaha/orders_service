import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { OrdersComponent } from "./orders/orders.component";
import { HeaderComponent } from "./header/header.component";

@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    styleUrl: './app.component.css',
    imports: [RouterOutlet, OrdersComponent, HeaderComponent]
})
export class AppComponent {
  title = 'orders_ui';
}
