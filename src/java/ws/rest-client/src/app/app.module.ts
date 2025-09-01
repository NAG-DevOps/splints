import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';


import { AppComponent } from './app.component';
import { ResultComponent } from './result/result.component';

import { RestService } from './rest.service';


@NgModule({ declarations: [
        AppComponent,
        ResultComponent
    ],
    bootstrap: [AppComponent], imports: [BrowserModule], providers: [RestService, provideHttpClient(withInterceptorsFromDi())] })
export class AppModule { }
