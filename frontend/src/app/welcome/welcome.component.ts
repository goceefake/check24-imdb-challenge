import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { WelcomeDataService } from '../service/data/welcome-data.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  name: '';

  constructor(private route:ActivatedRoute,
    private welcomeDataService: WelcomeDataService) { }

  ngOnInit(): void {
    console.log(this.route.snapshot.params['name']);
  }

  getWelcomeMessage() {
    console.log('welcome message');
    this.welcomeDataService.executeHelloWorldBean('user').subscribe(
      response => this.handleSuccessfulResponse(response),
      error => this.handleError(error)
    );
  }

  handleSuccessfulResponse(response) {
    console.log(response);
  }

  handleError(error) {
    console.log('error yaziliyor');
    console.log(error);
  }

}
