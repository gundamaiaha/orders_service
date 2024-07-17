import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AgGridAngular } from 'ag-grid-angular';
import { ColDef, GridApi, GridReadyEvent } from 'ag-grid-community';
import { OrdersService } from './orders.service';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CommonModule, DatePipe } from '@angular/common';
import { MAT_DATE_FORMATS, MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import {
  MatDatetimepickerModule,
  MatNativeDatetimeModule,
} from '@mat-datetimepicker/core';
import moment from 'moment';
export const MY_DATE_FORMATS = {
  parse: {
    dateInput: 'yyyy-MM-dd',
  },
  display: {
    dateInput: 'yyyy-MM-dd',
    monthYearLabel: 'MMM yyyy',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM yyyy',
  },
};

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [
    AgGridAngular,
    MatButtonModule,
    MatCardModule,
    MatButtonToggleModule,
    FormsModule,
    MatDatepickerModule,
    MatFormFieldModule,
    CommonModule,
    MatNativeDateModule,
    MatInputModule,
    MatDatetimepickerModule,
    MatNativeDatetimeModule,
  ],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css',
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS },
    DatePipe,
  ],
})
export class OrdersComponent implements OnInit {
  searchType = 'default';
  searchDate: string | null = null;
  private gridApi!: GridApi<any>;
  rowData: any[] = [];
  colDefs: ColDef[] = [
    {
      field: 'id',
      filter: true,
      editable: true,
      headerName: 'Order ID',
    },
    { field: 'customerName', filter: true, headerName: 'Customer Name' },
    { field: 'orderTime', filter: true, headerName: 'Order Placed At' },
    { field: 'status', filter: true, headerName: 'Order Status' },
  ];
  pagination: boolean = true;
  paginationPageSize: number = 10;
  paginationPageSizeSelector: boolean | number[] = [10, 100, 300];
  startDateTime: Date | null = null;
  endDateTime: Date | null = null;
  defaultColDef = {
    flex: 1,
    minWidth: 100,
  };

  constructor(
    private ordersService: OrdersService,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.ordersService
      .getAllOrders()
      .subscribe((data) => (this.rowData = data));
  }

  onDateChange(selectedDate: any): void {
    if (selectedDate) {
      this.searchDate = this.datePipe.transform(selectedDate, 'yyyy-MM-dd');
    } else {
      this.searchDate = null;
    }
  }
  formatDateTime(date: Date | null): string | null {
    return date ? moment(date).format('YYYY-MM-DDTHH:mm:ss') : null;
  }

  fetchSuccessOrders() {
    console.log('start Time -->' + this.formatDateTime(this.startDateTime));
    console.log('end time -->' + this.formatDateTime(this.endDateTime));
    const searchCriteria = this.buildSearchCriteria();
    this.ordersService
      .getSuccessOrders(searchCriteria)
      .subscribe((data) => (this.rowData = data));
  }

  fetchFailedOrders() {
    const searchCriteria = this.buildSearchCriteria();
    this.ordersService
      .getFailedOrders(searchCriteria)
      .subscribe((data) => (this.rowData = data));
  }

  buildSearchCriteria(): any {
    let searchCriteria: {
      searchDate: string | null;
      startDateTime: string | null;
      endDateTime: string | null;
    } = {
      searchDate: null,
      startDateTime: null,
      endDateTime: null,
    };

    if (this.searchType && this.searchType === 'searchWithDate') {
      searchCriteria.searchDate = this.searchDate;
    } else if (
      this.searchType &&
      this.searchType === 'searchWithDateAndTimes'
    ) {
      searchCriteria.startDateTime = this.formatDateTime(this.startDateTime);
      searchCriteria.endDateTime = this.formatDateTime(this.endDateTime);
    }

    return searchCriteria;
  }

  onExport() {
    console.log('Selected Date -->' + this.searchDate);
    const currentDate = new Date();
    const formattedDate = currentDate
      .toISOString()
      .slice(0, 19)
      .replace(/:/g, '-');
    const fileName = `OrdersExport_${formattedDate}.csv`;

    const params = {
      fileName: fileName,
    };
    this.gridApi.exportDataAsCsv(params);
  }

  onGridReady(params: GridReadyEvent) {
    this.gridApi = params.api;
  }
}
