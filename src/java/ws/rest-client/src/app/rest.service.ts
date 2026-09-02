import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

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
      .pipe(tap(data => console.log(JSON.stringify(data))));
  }

  private extractData(res: Response) {
    const body = res.json;
    return body;
  }
}
