import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { AppServiceService } from './app-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(private fb:FormBuilder,private service:AppServiceService, public sanitizer:DomSanitizer){}

  resfile!:any;
  forms!:FormGroup;

  ngOnInit(): void {
    this.forms = this.fb.group({
      "file":['',[Validators.required]]
    });

    this.getFile('1662480639423Aasan-Nahin-Yahan-(Aashiqui-2)-(Pagalworld.Com).mp3');
  }

  get file()
  {
    return this.forms.get('file');
  }

  onSelected(event:any)
  {
    console.log(event);
    if (event.target.files.length > 0) {
      const data = event.target.files[0];
      this.forms.patchValue({
        file: data
      });
    }
  }

  uploadFile(){
    console.log(this.forms.value);

    var formData = new FormData();
    formData.append('file',this.forms.get('file')?.value);

    console.log(formData)
    this.service.uploadFile(formData).subscribe({
      next:(res)=>{
        alert("File Uploaded");
        this.getFile(res.filename);
      },
      error:(err)=>
      {
        console.log(err)
      }
    })
  }
  
  getFile(fileName:string)
  {
    this.service.getFile(fileName).subscribe({
      next:(res)=>{
        let objectURL = URL.createObjectURL(res);

         this.resfile = this.sanitizer.bypassSecurityTrustUrl(objectURL);
        console.info(this.resfile)
      },
      error:(err)=>{
        console.warn(err)
      }
    });
  }


}
