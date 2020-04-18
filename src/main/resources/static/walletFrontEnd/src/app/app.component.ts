import { Component, OnInit, ElementRef } from '@angular/core';

@Component({
    selector: 'app',
    templateUrl: 'app.component.html'
})

export class AppComponent implements OnInit {
    constructor(private _elementRef: ElementRef) {

    }
    ngOnInit() {
        this._elementRef.nativeElement.ownerDocument.body.style.backgroundColor = '#242582';
    }
}
