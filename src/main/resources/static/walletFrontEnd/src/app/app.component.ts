import { Component, OnInit, ElementRef } from '@angular/core';

@Component({
    selector: 'app',
    templateUrl: 'app.component.html',
    styles: [`
    `]
})

export class AppComponent implements OnInit {
    constructor(private _elementRef: ElementRef) {

    }
    ngOnInit() {
        this._elementRef.nativeElement.ownerDocument.body.style.backgroundImage = 'linear-gradient(-90deg,#083373, #00b389)';
    }
}