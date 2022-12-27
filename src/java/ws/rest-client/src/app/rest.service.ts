import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/do';

export interface Issue {
  issues: string;
}

@Injectable()
export class RestService {

  private readonly bitbucketURL = 'https://api.bitbucket.org/1.0/repositories/soen487-w18-08/json-parser-issue-number/issues/';

  constructor(private http: HttpClient) { }

  getIssue(): Observable<Issue[]> {
    console.log('get issue service called!');
    return this.http.get<Issue[]>(this.bitbucketURL)
    .do(data => console.log(JSON.stringify(data)));
  }

  private extractData(res: Response) {
    const body = res.json;
    return body;
  }
}
