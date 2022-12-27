import { Component, OnInit } from '@angular/core';
import { RestService, Issue } from '../rest.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';


@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent implements OnInit {
  issues: Issue[] = [];
  constructor(private restService: RestService) { }

  ngOnInit() { }

  showIssues() {
    this.restService.getIssue().subscribe(issues => this.issues = issues);
  }

  clearResults() {
    console.log('results are cleared!');
    this.issues = [];
  }

}
